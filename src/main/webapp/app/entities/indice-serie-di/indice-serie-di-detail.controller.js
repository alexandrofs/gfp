(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('IndiceSerieDiDetailController', IndiceSerieDiDetailController);

    IndiceSerieDiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IndiceSerieDi'];

    function IndiceSerieDiDetailController($scope, $rootScope, $stateParams, previousState, entity, IndiceSerieDi) {
        var vm = this;

        vm.indiceSerieDi = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:indiceSerieDiUpdate', function(event, result) {
            vm.indiceSerieDi = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
