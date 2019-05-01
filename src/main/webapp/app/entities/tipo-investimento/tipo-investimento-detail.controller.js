(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoDetailController', TipoInvestimentoDetailController);

    TipoInvestimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoInvestimento', 'TipoImpostoRenda'];

    function TipoInvestimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoInvestimento, TipoImpostoRenda) {
        var vm = this;

        vm.tipoInvestimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:tipoInvestimentoUpdate', function(event, result) {
            vm.tipoInvestimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
