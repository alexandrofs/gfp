import { element, by, ElementFinder } from 'protractor';

export class HistoricoCotasComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-historico-cotas div table .btn-danger'));
    title = element.all(by.css('jhi-historico-cotas div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class HistoricoCotasUpdatePage {
    pageTitle = element(by.id('jhi-historico-cotas-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dataCotaInput = element(by.id('field_dataCota'));
    vlrCotaInput = element(by.id('field_vlrCota'));
    investimentoSelect = element(by.id('field_investimento'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDataCotaInput(dataCota) {
        await this.dataCotaInput.sendKeys(dataCota);
    }

    async getDataCotaInput() {
        return this.dataCotaInput.getAttribute('value');
    }

    async setVlrCotaInput(vlrCota) {
        await this.vlrCotaInput.sendKeys(vlrCota);
    }

    async getVlrCotaInput() {
        return this.vlrCotaInput.getAttribute('value');
    }

    async investimentoSelectLastOption() {
        await this.investimentoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async investimentoSelectOption(option) {
        await this.investimentoSelect.sendKeys(option);
    }

    getInvestimentoSelect(): ElementFinder {
        return this.investimentoSelect;
    }

    async getInvestimentoSelectedOption() {
        return this.investimentoSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class HistoricoCotasDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-historicoCotas-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-historicoCotas'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
