'use strict';

describe('Controller Tests', function() {

    describe('TipoImpostoRenda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTipoImpostoRenda, MockTabelaImpostoRenda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTipoImpostoRenda = jasmine.createSpy('MockTipoImpostoRenda');
            MockTabelaImpostoRenda = jasmine.createSpy('MockTabelaImpostoRenda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TipoImpostoRenda': MockTipoImpostoRenda,
                'TabelaImpostoRenda': MockTabelaImpostoRenda
            };
            createController = function() {
                $injector.get('$controller')("TipoImpostoRendaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:tipoImpostoRendaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
