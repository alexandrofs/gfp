(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('IndiceSerieDiImportController', IndiceSerieDiImportController);

    IndiceSerieDiImportController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'IndiceSerieDi', 'Upload'];

    function IndiceSerieDiImportController ($timeout, $scope, $stateParams, $uibModalInstance, IndiceSerieDi, Upload) {
        var vm = this;

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.importar = importar;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function importar () {
            vm.isSaving = true;
            var file = $scope.file;
            if ($scope.importForm.file.$valid && $scope.file) {
                Upload.upload({
                    url: 'api/indice-serie-dis/import',
                    data: {file: file}
                }).then(onImportSuccess, onImportError, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            } else {
            	alert('Selecione um arquivo');
            	vm.isSaving = false;
            }
        }

        function onImportSuccess (result) {
            $scope.$emit('gfpApp:indiceSerieDiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onImportError (resp) {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
