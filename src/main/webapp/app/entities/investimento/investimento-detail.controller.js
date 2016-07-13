(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InvestimentoDetailController', InvestimentoDetailController);

    InvestimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Investimento', 'Carteira', 'TipoInvestimento', 'HistoricoCotas'];

    function InvestimentoDetailController($scope, $rootScope, $stateParams, entity, Investimento, Carteira, TipoInvestimento, HistoricoCotas) {
        var vm = this;

        vm.investimento = entity;

        var unsubscribe = $rootScope.$on('gfpApp:investimentoUpdate', function(event, result) {
            vm.investimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
