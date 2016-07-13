(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('CarteiraDialogController', CarteiraDialogController);

    CarteiraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Carteira', 'Investimento'];

    function CarteiraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Carteira, Investimento) {
        var vm = this;

        vm.carteira = entity;
        vm.clear = clear;
        vm.save = save;
        vm.investimentos = Investimento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carteira.id !== null) {
                Carteira.update(vm.carteira, onSaveSuccess, onSaveError);
            } else {
                Carteira.save(vm.carteira, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:carteiraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
