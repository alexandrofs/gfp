/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoImpostoRendaComponentsPage, TipoImpostoRendaDeleteDialog, TipoImpostoRendaUpdatePage } from './tipo-imposto-renda.page-object';

const expect = chai.expect;

describe('TipoImpostoRenda e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoImpostoRendaUpdatePage: TipoImpostoRendaUpdatePage;
    let tipoImpostoRendaComponentsPage: TipoImpostoRendaComponentsPage;
    let tipoImpostoRendaDeleteDialog: TipoImpostoRendaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoImpostoRendas', async () => {
        await navBarPage.goToEntity('tipo-imposto-renda');
        tipoImpostoRendaComponentsPage = new TipoImpostoRendaComponentsPage();
        await browser.wait(ec.visibilityOf(tipoImpostoRendaComponentsPage.title), 5000);
        expect(await tipoImpostoRendaComponentsPage.getTitle()).to.eq('gfpApp.tipoImpostoRenda.home.title');
    });

    it('should load create TipoImpostoRenda page', async () => {
        await tipoImpostoRendaComponentsPage.clickOnCreateButton();
        tipoImpostoRendaUpdatePage = new TipoImpostoRendaUpdatePage();
        expect(await tipoImpostoRendaUpdatePage.getPageTitle()).to.eq('gfpApp.tipoImpostoRenda.home.createOrEditLabel');
        await tipoImpostoRendaUpdatePage.cancel();
    });

    it('should create and save TipoImpostoRendas', async () => {
        const nbButtonsBeforeCreate = await tipoImpostoRendaComponentsPage.countDeleteButtons();

        await tipoImpostoRendaComponentsPage.clickOnCreateButton();
        await promise.all([tipoImpostoRendaUpdatePage.setCodigoInput('codigo'), tipoImpostoRendaUpdatePage.setDescricaoInput('descricao')]);
        expect(await tipoImpostoRendaUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await tipoImpostoRendaUpdatePage.getDescricaoInput()).to.eq('descricao');
        await tipoImpostoRendaUpdatePage.save();
        expect(await tipoImpostoRendaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoImpostoRendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoImpostoRenda', async () => {
        const nbButtonsBeforeDelete = await tipoImpostoRendaComponentsPage.countDeleteButtons();
        await tipoImpostoRendaComponentsPage.clickOnLastDeleteButton();

        tipoImpostoRendaDeleteDialog = new TipoImpostoRendaDeleteDialog();
        expect(await tipoImpostoRendaDeleteDialog.getDialogTitle()).to.eq('gfpApp.tipoImpostoRenda.delete.question');
        await tipoImpostoRendaDeleteDialog.clickOnConfirmButton();

        expect(await tipoImpostoRendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
