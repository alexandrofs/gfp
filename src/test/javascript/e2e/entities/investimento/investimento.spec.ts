/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvestimentoComponentsPage, InvestimentoDeleteDialog, InvestimentoUpdatePage } from './investimento.page-object';

const expect = chai.expect;

describe('Investimento e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let investimentoUpdatePage: InvestimentoUpdatePage;
    let investimentoComponentsPage: InvestimentoComponentsPage;
    /*let investimentoDeleteDialog: InvestimentoDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Investimentos', async () => {
        await navBarPage.goToEntity('investimento');
        investimentoComponentsPage = new InvestimentoComponentsPage();
        await browser.wait(ec.visibilityOf(investimentoComponentsPage.title), 5000);
        expect(await investimentoComponentsPage.getTitle()).to.eq('gfpApp.investimento.home.title');
    });

    it('should load create Investimento page', async () => {
        await investimentoComponentsPage.clickOnCreateButton();
        investimentoUpdatePage = new InvestimentoUpdatePage();
        expect(await investimentoUpdatePage.getPageTitle()).to.eq('gfpApp.investimento.home.createOrEditLabel');
        await investimentoUpdatePage.cancel();
    });

    /* it('should create and save Investimentos', async () => {
        const nbButtonsBeforeCreate = await investimentoComponentsPage.countDeleteButtons();

        await investimentoComponentsPage.clickOnCreateButton();
        await promise.all([
            investimentoUpdatePage.setDataAplicacaoInput('2000-12-31'),
            investimentoUpdatePage.setQtdeCotaInput('5'),
            investimentoUpdatePage.setVlrCotaInput('5'),
            investimentoUpdatePage.setPctPrePosFixadoInput('5'),
            investimentoUpdatePage.carteiraSelectLastOption(),
            investimentoUpdatePage.tipoInvestimentoSelectLastOption(),
            investimentoUpdatePage.instituicaoSelectLastOption(),
        ]);
        expect(await investimentoUpdatePage.getDataAplicacaoInput()).to.eq('2000-12-31');
        expect(await investimentoUpdatePage.getQtdeCotaInput()).to.eq('5');
        expect(await investimentoUpdatePage.getVlrCotaInput()).to.eq('5');
        expect(await investimentoUpdatePage.getPctPrePosFixadoInput()).to.eq('5');
        await investimentoUpdatePage.save();
        expect(await investimentoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await investimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Investimento', async () => {
        const nbButtonsBeforeDelete = await investimentoComponentsPage.countDeleteButtons();
        await investimentoComponentsPage.clickOnLastDeleteButton();

        investimentoDeleteDialog = new InvestimentoDeleteDialog();
        expect(await investimentoDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.investimento.delete.question');
        await investimentoDeleteDialog.clickOnConfirmButton();

        expect(await investimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
