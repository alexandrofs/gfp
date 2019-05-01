(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InvestimentoDetailController', InvestimentoDetailController);

    InvestimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Investimento', 'Carteira', 'TipoInvestimento', 'Instituicao'];

    function InvestimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Investimento, Carteira, TipoInvestimento, Instituicao) {
        var vm = this;

        vm.investimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:investimentoUpdate', function(event, result) {
            vm.investimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
