(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('CarteiraDetailController', CarteiraDetailController);

    CarteiraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Carteira', 'Investimento'];

    function CarteiraDetailController($scope, $rootScope, $stateParams, previousState, entity, Carteira, Investimento) {
        var vm = this;

        vm.carteira = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:carteiraUpdate', function(event, result) {
            vm.carteira = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
