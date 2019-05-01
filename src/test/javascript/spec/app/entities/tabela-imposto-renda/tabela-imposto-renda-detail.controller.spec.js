'use strict';

describe('Controller Tests', function() {

    describe('TabelaImpostoRenda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTabelaImpostoRenda, MockTipoImpostoRenda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTabelaImpostoRenda = jasmine.createSpy('MockTabelaImpostoRenda');
            MockTipoImpostoRenda = jasmine.createSpy('MockTipoImpostoRenda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TabelaImpostoRenda': MockTabelaImpostoRenda,
                'TipoImpostoRenda': MockTipoImpostoRenda
            };
            createController = function() {
                $injector.get('$controller')("TabelaImpostoRendaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:tabelaImpostoRendaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
