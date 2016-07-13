(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('CarteiraDetailController', CarteiraDetailController);

    CarteiraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Carteira', 'Investimento'];

    function CarteiraDetailController($scope, $rootScope, $stateParams, entity, Carteira, Investimento) {
        var vm = this;

        vm.carteira = entity;

        var unsubscribe = $rootScope.$on('gfpApp:carteiraUpdate', function(event, result) {
            vm.carteira = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
