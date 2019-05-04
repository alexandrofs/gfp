import { element, by, ElementFinder } from 'protractor';

export class TipoInvestimentoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-tipo-investimento div table .btn-danger'));
    title = element.all(by.css('jhi-tipo-investimento div h2#page-heading span')).first();

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

export class TipoInvestimentoUpdatePage {
    pageTitle = element(by.id('jhi-tipo-investimento-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomeInput = element(by.id('field_nome'));
    descricaoInput = element(by.id('field_descricao'));
    modalidadeInput = element(by.id('field_modalidade'));
    tipoIndexadorInput = element(by.id('field_tipoIndexador'));
    indiceInput = element(by.id('field_indice'));
    tipoImpostoRendaSelect = element(by.id('field_tipoImpostoRenda'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomeInput(nome) {
        await this.nomeInput.sendKeys(nome);
    }

    async getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    async setDescricaoInput(descricao) {
        await this.descricaoInput.sendKeys(descricao);
    }

    async getDescricaoInput() {
        return this.descricaoInput.getAttribute('value');
    }

    async setModalidadeInput(modalidade) {
        await this.modalidadeInput.sendKeys(modalidade);
    }

    async getModalidadeInput() {
        return this.modalidadeInput.getAttribute('value');
    }

    async setTipoIndexadorInput(tipoIndexador) {
        await this.tipoIndexadorInput.sendKeys(tipoIndexador);
    }

    async getTipoIndexadorInput() {
        return this.tipoIndexadorInput.getAttribute('value');
    }

    async setIndiceInput(indice) {
        await this.indiceInput.sendKeys(indice);
    }

    async getIndiceInput() {
        return this.indiceInput.getAttribute('value');
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

export class TipoInvestimentoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tipoInvestimento-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tipoInvestimento'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
