(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoDetailController', TipoInvestimentoDetailController);

    TipoInvestimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TipoInvestimento', 'TipoImpostoRenda'];

    function TipoInvestimentoDetailController($scope, $rootScope, $stateParams, entity, TipoInvestimento, TipoImpostoRenda) {
        var vm = this;

        vm.tipoInvestimento = entity;

        var unsubscribe = $rootScope.$on('gfpApp:tipoInvestimentoUpdate', function(event, result) {
            vm.tipoInvestimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
