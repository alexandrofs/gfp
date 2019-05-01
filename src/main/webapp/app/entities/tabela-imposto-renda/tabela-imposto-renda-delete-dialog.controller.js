(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TabelaImpostoRendaDeleteController',TabelaImpostoRendaDeleteController);

    TabelaImpostoRendaDeleteController.$inject = ['$uibModalInstance', 'entity', 'TabelaImpostoRenda'];

    function TabelaImpostoRendaDeleteController($uibModalInstance, entity, TabelaImpostoRenda) {
        var vm = this;

        vm.tabelaImpostoRenda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TabelaImpostoRenda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
