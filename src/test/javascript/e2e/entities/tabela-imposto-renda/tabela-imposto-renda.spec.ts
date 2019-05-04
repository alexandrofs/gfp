/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TabelaImpostoRendaComponentsPage,
    TabelaImpostoRendaDeleteDialog,
    TabelaImpostoRendaUpdatePage
} from './tabela-imposto-renda.page-object';

const expect = chai.expect;

describe('TabelaImpostoRenda e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tabelaImpostoRendaUpdatePage: TabelaImpostoRendaUpdatePage;
    let tabelaImpostoRendaComponentsPage: TabelaImpostoRendaComponentsPage;
    /*let tabelaImpostoRendaDeleteDialog: TabelaImpostoRendaDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TabelaImpostoRendas', async () => {
        await navBarPage.goToEntity('tabela-imposto-renda');
        tabelaImpostoRendaComponentsPage = new TabelaImpostoRendaComponentsPage();
        await browser.wait(ec.visibilityOf(tabelaImpostoRendaComponentsPage.title), 5000);
        expect(await tabelaImpostoRendaComponentsPage.getTitle()).to.eq('gfpApp.tabelaImpostoRenda.home.title');
    });

    it('should load create TabelaImpostoRenda page', async () => {
        await tabelaImpostoRendaComponentsPage.clickOnCreateButton();
        tabelaImpostoRendaUpdatePage = new TabelaImpostoRendaUpdatePage();
        expect(await tabelaImpostoRendaUpdatePage.getPageTitle()).to.eq('gfpApp.tabelaImpostoRenda.home.createOrEditLabel');
        await tabelaImpostoRendaUpdatePage.cancel();
    });

    /* it('should create and save TabelaImpostoRendas', async () => {
        const nbButtonsBeforeCreate = await tabelaImpostoRendaComponentsPage.countDeleteButtons();

        await tabelaImpostoRendaComponentsPage.clickOnCreateButton();
        await promise.all([
            tabelaImpostoRendaUpdatePage.setNumDiasInput('5'),
            tabelaImpostoRendaUpdatePage.setPctAliquotaInput('5'),
            tabelaImpostoRendaUpdatePage.tipoImpostoRendaSelectLastOption(),
        ]);
        expect(await tabelaImpostoRendaUpdatePage.getNumDiasInput()).to.eq('5');
        expect(await tabelaImpostoRendaUpdatePage.getPctAliquotaInput()).to.eq('5');
        await tabelaImpostoRendaUpdatePage.save();
        expect(await tabelaImpostoRendaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tabelaImpostoRendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last TabelaImpostoRenda', async () => {
        const nbButtonsBeforeDelete = await tabelaImpostoRendaComponentsPage.countDeleteButtons();
        await tabelaImpostoRendaComponentsPage.clickOnLastDeleteButton();

        tabelaImpostoRendaDeleteDialog = new TabelaImpostoRendaDeleteDialog();
        expect(await tabelaImpostoRendaDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.tabelaImpostoRenda.delete.question');
        await tabelaImpostoRendaDeleteDialog.clickOnConfirmButton();

        expect(await tabelaImpostoRendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
