import { element, by, ElementFinder } from 'protractor';

export class IndiceSerieDiComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-indice-serie-di div table .btn-danger'));
    title = element.all(by.css('jhi-indice-serie-di div h2#page-heading span')).first();

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

export class IndiceSerieDiUpdatePage {
    pageTitle = element(by.id('jhi-indice-serie-di-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dataInput = element(by.id('field_data'));
    taxaMediaAnualInput = element(by.id('field_taxaMediaAnual'));
    taxaSelicInput = element(by.id('field_taxaSelic'));
    fatorDiarioInput = element(by.id('field_fatorDiario'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDataInput(data) {
        await this.dataInput.sendKeys(data);
    }

    async getDataInput() {
        return this.dataInput.getAttribute('value');
    }

    async setTaxaMediaAnualInput(taxaMediaAnual) {
        await this.taxaMediaAnualInput.sendKeys(taxaMediaAnual);
    }

    async getTaxaMediaAnualInput() {
        return this.taxaMediaAnualInput.getAttribute('value');
    }

    async setTaxaSelicInput(taxaSelic) {
        await this.taxaSelicInput.sendKeys(taxaSelic);
    }

    async getTaxaSelicInput() {
        return this.taxaSelicInput.getAttribute('value');
    }

    async setFatorDiarioInput(fatorDiario) {
        await this.fatorDiarioInput.sendKeys(fatorDiario);
    }

    async getFatorDiarioInput() {
        return this.fatorDiarioInput.getAttribute('value');
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

export class IndiceSerieDiDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-indiceSerieDi-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-indiceSerieDi'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
