(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoDeleteController',TipoInvestimentoDeleteController);

    TipoInvestimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoInvestimento'];

    function TipoInvestimentoDeleteController($uibModalInstance, entity, TipoInvestimento) {
        var vm = this;

        vm.tipoInvestimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoInvestimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
