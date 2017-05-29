(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoDialogController', TipoInvestimentoDialogController);

    TipoInvestimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoInvestimento', 'TipoImpostoRenda', 'MODALIDADE_CDB', 'MODALIDADE_LCI', 'MODALIDADE_TESOURO', 'INDEXADOR_PRE', 'INDEXADOR_POS', 'INDICE_DI', 'INDICE_IPCA', 'ModalidadeService'];

    function TipoInvestimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoInvestimento, TipoImpostoRenda, MODALIDADE_CDB, MODALIDADE_LCI, MODALIDADE_TESOURO, INDEXADOR_PRE, INDEXADOR_POS, INDICE_DI, INDICE_IPCA, ModalidadeService) {
        var vm = this;

        vm.MODALIDADE_CDB = MODALIDADE_CDB;
        vm.MODALIDADE_LCI = MODALIDADE_LCI;
        
        vm.INDEXADOR_POS = INDEXADOR_POS;
        
        vm.tipoInvestimento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipoimpostorendas = TipoImpostoRenda.query();
        vm.tipoModalidades = ModalidadeService.query();
        vm.tipoIndexadores = [INDEXADOR_PRE, INDEXADOR_POS];
        vm.indices = [INDICE_DI, INDICE_IPCA];

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoInvestimento.modalidade != MODALIDADE_CDB && vm.tipoInvestimento.modalidade != MODALIDADE_LCI) {
            	vm.tipoInvestimento.tipoIndexador = null;
            }
            if (vm.tipoInvestimento.tipoIndexador != INDEXADOR_POS) {
            	vm.tipoInvestimento.indice = null;
            }
            if (vm.tipoInvestimento.id !== null) {
                TipoInvestimento.update(vm.tipoInvestimento, onSaveSuccess, onSaveError);
            } else {
                TipoInvestimento.save(vm.tipoInvestimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:tipoInvestimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
