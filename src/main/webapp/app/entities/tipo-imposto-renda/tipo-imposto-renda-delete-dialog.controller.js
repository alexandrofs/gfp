(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoImpostoRendaDeleteController',TipoImpostoRendaDeleteController);

    TipoImpostoRendaDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoImpostoRenda'];

    function TipoImpostoRendaDeleteController($uibModalInstance, entity, TipoImpostoRenda) {
        var vm = this;

        vm.tipoImpostoRenda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoImpostoRenda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
