(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoImpostoRendaDialogController', TipoImpostoRendaDialogController);

    TipoImpostoRendaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoImpostoRenda', 'TabelaImpostoRenda'];

    function TipoImpostoRendaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoImpostoRenda, TabelaImpostoRenda) {
        var vm = this;

        vm.tipoImpostoRenda = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tabelaimpostorendas = TabelaImpostoRenda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoImpostoRenda.id !== null) {
                TipoImpostoRenda.update(vm.tipoImpostoRenda, onSaveSuccess, onSaveError);
            } else {
                TipoImpostoRenda.save(vm.tipoImpostoRenda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:tipoImpostoRendaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
