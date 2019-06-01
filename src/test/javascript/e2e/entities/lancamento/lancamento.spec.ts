/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LancamentoComponentsPage, LancamentoDeleteDialog, LancamentoUpdatePage } from './lancamento.page-object';

const expect = chai.expect;

describe('Lancamento e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let lancamentoUpdatePage: LancamentoUpdatePage;
    let lancamentoComponentsPage: LancamentoComponentsPage;
    /*let lancamentoDeleteDialog: LancamentoDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Lancamentos', async () => {
        await navBarPage.goToEntity('lancamento');
        lancamentoComponentsPage = new LancamentoComponentsPage();
        await browser.wait(ec.visibilityOf(lancamentoComponentsPage.title), 5000);
        expect(await lancamentoComponentsPage.getTitle()).to.eq('gfpApp.lancamento.home.title');
    });

    it('should load create Lancamento page', async () => {
        await lancamentoComponentsPage.clickOnCreateButton();
        lancamentoUpdatePage = new LancamentoUpdatePage();
        expect(await lancamentoUpdatePage.getPageTitle()).to.eq('gfpApp.lancamento.home.createOrEditLabel');
        await lancamentoUpdatePage.cancel();
    });

    /* it('should create and save Lancamentos', async () => {
        const nbButtonsBeforeCreate = await lancamentoComponentsPage.countDeleteButtons();

        await lancamentoComponentsPage.clickOnCreateButton();
        await promise.all([
            lancamentoUpdatePage.setDataInput('2000-12-31'),
            lancamentoUpdatePage.setDescricaoInput('descricao'),
            lancamentoUpdatePage.setValorInput('5'),
            lancamentoUpdatePage.setUsuarioInput('usuario'),
            lancamentoUpdatePage.contaPagamentoSelectLastOption(),
        ]);
        expect(await lancamentoUpdatePage.getDataInput()).to.eq('2000-12-31');
        expect(await lancamentoUpdatePage.getDescricaoInput()).to.eq('descricao');
        expect(await lancamentoUpdatePage.getValorInput()).to.eq('5');
        expect(await lancamentoUpdatePage.getUsuarioInput()).to.eq('usuario');
        await lancamentoUpdatePage.save();
        expect(await lancamentoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await lancamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Lancamento', async () => {
        const nbButtonsBeforeDelete = await lancamentoComponentsPage.countDeleteButtons();
        await lancamentoComponentsPage.clickOnLastDeleteButton();

        lancamentoDeleteDialog = new LancamentoDeleteDialog();
        expect(await lancamentoDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.lancamento.delete.question');
        await lancamentoDeleteDialog.clickOnConfirmButton();

        expect(await lancamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
