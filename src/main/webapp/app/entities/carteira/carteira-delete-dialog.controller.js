(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('CarteiraDeleteController',CarteiraDeleteController);

    CarteiraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Carteira'];

    function CarteiraDeleteController($uibModalInstance, entity, Carteira) {
        var vm = this;

        vm.carteira = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Carteira.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
