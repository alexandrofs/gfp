(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('IndiceSerieDiDialogController', IndiceSerieDiDialogController);

    IndiceSerieDiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IndiceSerieDi'];

    function IndiceSerieDiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IndiceSerieDi) {
        var vm = this;

        vm.indiceSerieDi = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.indiceSerieDi.id !== null) {
                IndiceSerieDi.update(vm.indiceSerieDi, onSaveSuccess, onSaveError);
            } else {
                IndiceSerieDi.save(vm.indiceSerieDi, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gfpApp:indiceSerieDiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
