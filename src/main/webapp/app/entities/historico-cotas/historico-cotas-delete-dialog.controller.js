(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('HistoricoCotasDeleteController',HistoricoCotasDeleteController);

    HistoricoCotasDeleteController.$inject = ['$uibModalInstance', 'entity', 'HistoricoCotas'];

    function HistoricoCotasDeleteController($uibModalInstance, entity, HistoricoCotas) {
        var vm = this;

        vm.historicoCotas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HistoricoCotas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
