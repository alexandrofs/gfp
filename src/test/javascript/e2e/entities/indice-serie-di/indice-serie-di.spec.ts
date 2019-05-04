/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IndiceSerieDiComponentsPage, IndiceSerieDiDeleteDialog, IndiceSerieDiUpdatePage } from './indice-serie-di.page-object';

const expect = chai.expect;

describe('IndiceSerieDi e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let indiceSerieDiUpdatePage: IndiceSerieDiUpdatePage;
    let indiceSerieDiComponentsPage: IndiceSerieDiComponentsPage;
    let indiceSerieDiDeleteDialog: IndiceSerieDiDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load IndiceSerieDis', async () => {
        await navBarPage.goToEntity('indice-serie-di');
        indiceSerieDiComponentsPage = new IndiceSerieDiComponentsPage();
        await browser.wait(ec.visibilityOf(indiceSerieDiComponentsPage.title), 5000);
        expect(await indiceSerieDiComponentsPage.getTitle()).to.eq('gfpApp.indiceSerieDi.home.title');
    });

    it('should load create IndiceSerieDi page', async () => {
        await indiceSerieDiComponentsPage.clickOnCreateButton();
        indiceSerieDiUpdatePage = new IndiceSerieDiUpdatePage();
        expect(await indiceSerieDiUpdatePage.getPageTitle()).to.eq('gfpApp.indiceSerieDi.home.createOrEditLabel');
        await indiceSerieDiUpdatePage.cancel();
    });

    it('should create and save IndiceSerieDis', async () => {
        const nbButtonsBeforeCreate = await indiceSerieDiComponentsPage.countDeleteButtons();

        await indiceSerieDiComponentsPage.clickOnCreateButton();
        await promise.all([
            indiceSerieDiUpdatePage.setDataInput('2000-12-31'),
            indiceSerieDiUpdatePage.setTaxaMediaAnualInput('5'),
            indiceSerieDiUpdatePage.setTaxaSelicInput('5'),
            indiceSerieDiUpdatePage.setFatorDiarioInput('5')
        ]);
        expect(await indiceSerieDiUpdatePage.getDataInput()).to.eq('2000-12-31');
        expect(await indiceSerieDiUpdatePage.getTaxaMediaAnualInput()).to.eq('5');
        expect(await indiceSerieDiUpdatePage.getTaxaSelicInput()).to.eq('5');
        expect(await indiceSerieDiUpdatePage.getFatorDiarioInput()).to.eq('5');
        await indiceSerieDiUpdatePage.save();
        expect(await indiceSerieDiUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await indiceSerieDiComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last IndiceSerieDi', async () => {
        const nbButtonsBeforeDelete = await indiceSerieDiComponentsPage.countDeleteButtons();
        await indiceSerieDiComponentsPage.clickOnLastDeleteButton();

        indiceSerieDiDeleteDialog = new IndiceSerieDiDeleteDialog();
        expect(await indiceSerieDiDeleteDialog.getDialogTitle()).to.eq('gfpApp.indiceSerieDi.delete.question');
        await indiceSerieDiDeleteDialog.clickOnConfirmButton();

        expect(await indiceSerieDiComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
