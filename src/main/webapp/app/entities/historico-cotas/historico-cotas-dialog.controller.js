(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('HistoricoCotasDialogController', HistoricoCotasDialogController);

    HistoricoCotasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HistoricoCotas', 'Investimento'];

    function HistoricoCotasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HistoricoCotas, Investimento) {
        var vm = this;

        vm.historicoCotas = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.historicoCotas.id !== null) {
                HistoricoCotas.update(vm.historicoCotas, onSaveSuccess, onSaveError);
            } else {
                HistoricoCotas.save(vm.historicoCotas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:historicoCotasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataCota = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
