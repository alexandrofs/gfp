import { element, by, ElementFinder } from 'protractor';

export class TabelaImpostoRendaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-tabela-imposto-renda div table .btn-danger'));
    title = element.all(by.css('jhi-tabela-imposto-renda div h2#page-heading span')).first();

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

export class TabelaImpostoRendaUpdatePage {
    pageTitle = element(by.id('jhi-tabela-imposto-renda-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    numDiasInput = element(by.id('field_numDias'));
    pctAliquotaInput = element(by.id('field_pctAliquota'));
    tipoImpostoRendaSelect = element(by.id('field_tipoImpostoRenda'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNumDiasInput(numDias) {
        await this.numDiasInput.sendKeys(numDias);
    }

    async getNumDiasInput() {
        return this.numDiasInput.getAttribute('value');
    }

    async setPctAliquotaInput(pctAliquota) {
        await this.pctAliquotaInput.sendKeys(pctAliquota);
    }

    async getPctAliquotaInput() {
        return this.pctAliquotaInput.getAttribute('value');
    }

    async tipoImpostoRendaSelectLastOption() {
        await this.tipoImpostoRendaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoImpostoRendaSelectOption(option) {
        await this.tipoImpostoRendaSelect.sendKeys(option);
    }

    getTipoImpostoRendaSelect(): ElementFinder {
        return this.tipoImpostoRendaSelect;
    }

    async getTipoImpostoRendaSelectedOption() {
        return this.tipoImpostoRendaSelect.element(by.css('option:checked')).getText();
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

export class TabelaImpostoRendaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tabelaImpostoRenda-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tabelaImpostoRenda'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
