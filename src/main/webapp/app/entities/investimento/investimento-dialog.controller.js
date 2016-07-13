(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InvestimentoDialogController', InvestimentoDialogController);

    InvestimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Investimento', 'Carteira', 'TipoInvestimento', 'HistoricoCotas'];

    function InvestimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Investimento, Carteira, TipoInvestimento, HistoricoCotas) {
        var vm = this;

        vm.investimento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.carteiras = Carteira.query();
        vm.tipoinvestimentos = TipoInvestimento.query();
        vm.historicocotas = HistoricoCotas.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.investimento.id !== null) {
                Investimento.update(vm.investimento, onSaveSuccess, onSaveError);
            } else {
                Investimento.save(vm.investimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:investimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataAplicacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
