/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContaPagamentoComponentsPage, ContaPagamentoDeleteDialog, ContaPagamentoUpdatePage } from './conta-pagamento.page-object';

const expect = chai.expect;

describe('ContaPagamento e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contaPagamentoUpdatePage: ContaPagamentoUpdatePage;
    let contaPagamentoComponentsPage: ContaPagamentoComponentsPage;
    let contaPagamentoDeleteDialog: ContaPagamentoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContaPagamentos', async () => {
        await navBarPage.goToEntity('conta-pagamento');
        contaPagamentoComponentsPage = new ContaPagamentoComponentsPage();
        await browser.wait(ec.visibilityOf(contaPagamentoComponentsPage.title), 5000);
        expect(await contaPagamentoComponentsPage.getTitle()).to.eq('gfpApp.contaPagamento.home.title');
    });

    it('should load create ContaPagamento page', async () => {
        await contaPagamentoComponentsPage.clickOnCreateButton();
        contaPagamentoUpdatePage = new ContaPagamentoUpdatePage();
        expect(await contaPagamentoUpdatePage.getPageTitle()).to.eq('gfpApp.contaPagamento.home.createOrEditLabel');
        await contaPagamentoUpdatePage.cancel();
    });

    it('should create and save ContaPagamentos', async () => {
        const nbButtonsBeforeCreate = await contaPagamentoComponentsPage.countDeleteButtons();

        await contaPagamentoComponentsPage.clickOnCreateButton();
        await promise.all([
            contaPagamentoUpdatePage.setNomeInput('nome'),
            contaPagamentoUpdatePage.tipoContaSelectLastOption(),
            contaPagamentoUpdatePage.setUsuarioInput('usuario')
        ]);
        expect(await contaPagamentoUpdatePage.getNomeInput()).to.eq('nome');
        expect(await contaPagamentoUpdatePage.getUsuarioInput()).to.eq('usuario');
        await contaPagamentoUpdatePage.save();
        expect(await contaPagamentoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contaPagamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ContaPagamento', async () => {
        const nbButtonsBeforeDelete = await contaPagamentoComponentsPage.countDeleteButtons();
        await contaPagamentoComponentsPage.clickOnLastDeleteButton();

        contaPagamentoDeleteDialog = new ContaPagamentoDeleteDialog();
        expect(await contaPagamentoDeleteDialog.getDialogTitle()).to.eq('gfpApp.contaPagamento.delete.question');
        await contaPagamentoDeleteDialog.clickOnConfirmButton();

        expect(await contaPagamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
