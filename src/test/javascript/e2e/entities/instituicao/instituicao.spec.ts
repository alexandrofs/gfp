/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InstituicaoComponentsPage, InstituicaoDeleteDialog, InstituicaoUpdatePage } from './instituicao.page-object';

const expect = chai.expect;

describe('Instituicao e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let instituicaoUpdatePage: InstituicaoUpdatePage;
    let instituicaoComponentsPage: InstituicaoComponentsPage;
    let instituicaoDeleteDialog: InstituicaoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Instituicaos', async () => {
        await navBarPage.goToEntity('instituicao');
        instituicaoComponentsPage = new InstituicaoComponentsPage();
        await browser.wait(ec.visibilityOf(instituicaoComponentsPage.title), 5000);
        expect(await instituicaoComponentsPage.getTitle()).to.eq('gfpApp.instituicao.home.title');
    });

    it('should load create Instituicao page', async () => {
        await instituicaoComponentsPage.clickOnCreateButton();
        instituicaoUpdatePage = new InstituicaoUpdatePage();
        expect(await instituicaoUpdatePage.getPageTitle()).to.eq('gfpApp.instituicao.home.createOrEditLabel');
        await instituicaoUpdatePage.cancel();
    });

    it('should create and save Instituicaos', async () => {
        const nbButtonsBeforeCreate = await instituicaoComponentsPage.countDeleteButtons();

        await instituicaoComponentsPage.clickOnCreateButton();
        await promise.all([instituicaoUpdatePage.setNomeInput('nome')]);
        expect(await instituicaoUpdatePage.getNomeInput()).to.eq('nome');
        await instituicaoUpdatePage.save();
        expect(await instituicaoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await instituicaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Instituicao', async () => {
        const nbButtonsBeforeDelete = await instituicaoComponentsPage.countDeleteButtons();
        await instituicaoComponentsPage.clickOnLastDeleteButton();

        instituicaoDeleteDialog = new InstituicaoDeleteDialog();
        expect(await instituicaoDeleteDialog.getDialogTitle()).to.eq('gfpApp.instituicao.delete.question');
        await instituicaoDeleteDialog.clickOnConfirmButton();

        expect(await instituicaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
