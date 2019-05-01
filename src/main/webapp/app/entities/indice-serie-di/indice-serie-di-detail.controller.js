(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('IndiceSerieDiDetailController', IndiceSerieDiDetailController);

    IndiceSerieDiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'IndiceSerieDi'];

    function IndiceSerieDiDetailController($scope, $rootScope, $stateParams, entity, IndiceSerieDi) {
        var vm = this;

        vm.indiceSerieDi = entity;

        var unsubscribe = $rootScope.$on('gfpApp:indiceSerieDiUpdate', function(event, result) {
            vm.indiceSerieDi = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
