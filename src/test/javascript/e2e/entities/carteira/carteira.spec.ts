/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CarteiraComponentsPage, CarteiraDeleteDialog, CarteiraUpdatePage } from './carteira.page-object';

const expect = chai.expect;

describe('Carteira e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let carteiraUpdatePage: CarteiraUpdatePage;
    let carteiraComponentsPage: CarteiraComponentsPage;
    let carteiraDeleteDialog: CarteiraDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Carteiras', async () => {
        await navBarPage.goToEntity('carteira');
        carteiraComponentsPage = new CarteiraComponentsPage();
        await browser.wait(ec.visibilityOf(carteiraComponentsPage.title), 5000);
        expect(await carteiraComponentsPage.getTitle()).to.eq('gfpApp.carteira.home.title');
    });

    it('should load create Carteira page', async () => {
        await carteiraComponentsPage.clickOnCreateButton();
        carteiraUpdatePage = new CarteiraUpdatePage();
        expect(await carteiraUpdatePage.getPageTitle()).to.eq('gfpApp.carteira.home.createOrEditLabel');
        await carteiraUpdatePage.cancel();
    });

    it('should create and save Carteiras', async () => {
        const nbButtonsBeforeCreate = await carteiraComponentsPage.countDeleteButtons();

        await carteiraComponentsPage.clickOnCreateButton();
        await promise.all([carteiraUpdatePage.setNomeInput('nome'), carteiraUpdatePage.setDescricaoInput('descricao')]);
        expect(await carteiraUpdatePage.getNomeInput()).to.eq('nome');
        expect(await carteiraUpdatePage.getDescricaoInput()).to.eq('descricao');
        await carteiraUpdatePage.save();
        expect(await carteiraUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await carteiraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Carteira', async () => {
        const nbButtonsBeforeDelete = await carteiraComponentsPage.countDeleteButtons();
        await carteiraComponentsPage.clickOnLastDeleteButton();

        carteiraDeleteDialog = new CarteiraDeleteDialog();
        expect(await carteiraDeleteDialog.getDialogTitle()).to.eq('gfpApp.carteira.delete.question');
        await carteiraDeleteDialog.clickOnConfirmButton();

        expect(await carteiraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
