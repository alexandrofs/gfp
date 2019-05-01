(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('IndiceSerieDiDeleteController',IndiceSerieDiDeleteController);

    IndiceSerieDiDeleteController.$inject = ['$uibModalInstance', 'entity', 'IndiceSerieDi'];

    function IndiceSerieDiDeleteController($uibModalInstance, entity, IndiceSerieDi) {
        var vm = this;

        vm.indiceSerieDi = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IndiceSerieDi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
