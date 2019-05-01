(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('TipoInvestimentoDetailController', TipoInvestimentoDetailController);

    TipoInvestimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoInvestimento', 'TipoImpostoRenda', 'ModalidadeService'];

    function TipoInvestimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoInvestimento, TipoImpostoRenda, ModalidadeService) {
        var vm = this;

        vm.tipoInvestimento = entity;
        vm.previousState = previousState.name;
        vm.modalidades = ModalidadeService.query();

        var unsubscribe = $rootScope.$on('gfpApp:tipoInvestimentoUpdate', function(event, result) {
            vm.tipoInvestimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
