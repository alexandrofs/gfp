(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TabelaImpostoRendaDialogController', TabelaImpostoRendaDialogController);

    TabelaImpostoRendaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TabelaImpostoRenda', 'TipoImpostoRenda'];

    function TabelaImpostoRendaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TabelaImpostoRenda, TipoImpostoRenda) {
        var vm = this;

        vm.tabelaImpostoRenda = entity;
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
            if (vm.tabelaImpostoRenda.id !== null) {
                TabelaImpostoRenda.update(vm.tabelaImpostoRenda, onSaveSuccess, onSaveError);
            } else {
                TabelaImpostoRenda.save(vm.tabelaImpostoRenda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:tabelaImpostoRendaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
