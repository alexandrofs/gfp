(function() {
    'use strict';

    angular
        .module('gfpApp')
        .controller('InstituicaoDetailController', InstituicaoDetailController);

    InstituicaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Instituicao'];

    function InstituicaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Instituicao) {
        var vm = this;

        vm.instituicao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gfpApp:instituicaoUpdate', function(event, result) {
            vm.instituicao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
