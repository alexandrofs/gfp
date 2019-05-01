(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoDialogController', TipoInvestimentoDialogController);

    TipoInvestimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoInvestimento', 'TipoImpostoRenda'];

    function TipoInvestimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoInvestimento, TipoImpostoRenda) {
        var vm = this;

        vm.tipoInvestimento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipoimpostorendas = TipoImpostoRenda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
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
