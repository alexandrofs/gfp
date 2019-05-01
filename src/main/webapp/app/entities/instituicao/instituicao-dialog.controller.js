(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InstituicaoDialogController', InstituicaoDialogController);

    InstituicaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Instituicao'];

    function InstituicaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Instituicao) {
        var vm = this;

        vm.instituicao = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.instituicao.id !== null) {
                Instituicao.update(vm.instituicao, onSaveSuccess, onSaveError);
            } else {
                Instituicao.save(vm.instituicao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:instituicaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
