/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { HistoricoCotasComponentsPage, HistoricoCotasDeleteDialog, HistoricoCotasUpdatePage } from './historico-cotas.page-object';

const expect = chai.expect;

describe('HistoricoCotas e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let historicoCotasUpdatePage: HistoricoCotasUpdatePage;
    let historicoCotasComponentsPage: HistoricoCotasComponentsPage;
    /*let historicoCotasDeleteDialog: HistoricoCotasDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load HistoricoCotas', async () => {
        await navBarPage.goToEntity('historico-cotas');
        historicoCotasComponentsPage = new HistoricoCotasComponentsPage();
        await browser.wait(ec.visibilityOf(historicoCotasComponentsPage.title), 5000);
        expect(await historicoCotasComponentsPage.getTitle()).to.eq('gfpApp.historicoCotas.home.title');
    });

    it('should load create HistoricoCotas page', async () => {
        await historicoCotasComponentsPage.clickOnCreateButton();
        historicoCotasUpdatePage = new HistoricoCotasUpdatePage();
        expect(await historicoCotasUpdatePage.getPageTitle()).to.eq('gfpApp.historicoCotas.home.createOrEditLabel');
        await historicoCotasUpdatePage.cancel();
    });

    /* it('should create and save HistoricoCotas', async () => {
        const nbButtonsBeforeCreate = await historicoCotasComponentsPage.countDeleteButtons();

        await historicoCotasComponentsPage.clickOnCreateButton();
        await promise.all([
            historicoCotasUpdatePage.setDataCotaInput('2000-12-31'),
            historicoCotasUpdatePage.setVlrCotaInput('5'),
            historicoCotasUpdatePage.investimentoSelectLastOption(),
        ]);
        expect(await historicoCotasUpdatePage.getDataCotaInput()).to.eq('2000-12-31');
        expect(await historicoCotasUpdatePage.getVlrCotaInput()).to.eq('5');
        await historicoCotasUpdatePage.save();
        expect(await historicoCotasUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await historicoCotasComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last HistoricoCotas', async () => {
        const nbButtonsBeforeDelete = await historicoCotasComponentsPage.countDeleteButtons();
        await historicoCotasComponentsPage.clickOnLastDeleteButton();

        historicoCotasDeleteDialog = new HistoricoCotasDeleteDialog();
        expect(await historicoCotasDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.historicoCotas.delete.question');
        await historicoCotasDeleteDialog.clickOnConfirmButton();

        expect(await historicoCotasComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
