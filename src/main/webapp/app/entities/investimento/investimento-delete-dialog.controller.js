(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InvestimentoDeleteController',InvestimentoDeleteController);

    InvestimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Investimento'];

    function InvestimentoDeleteController($uibModalInstance, entity, Investimento) {
        var vm = this;

        vm.investimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Investimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
