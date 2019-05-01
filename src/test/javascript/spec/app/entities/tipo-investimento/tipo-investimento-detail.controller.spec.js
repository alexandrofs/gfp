'use strict';

describe('Controller Tests', function() {

    describe('TipoInvestimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTipoInvestimento, MockTipoImpostoRenda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTipoInvestimento = jasmine.createSpy('MockTipoInvestimento');
            MockTipoImpostoRenda = jasmine.createSpy('MockTipoImpostoRenda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TipoInvestimento': MockTipoInvestimento,
                'TipoImpostoRenda': MockTipoImpostoRenda
            };
            createController = function() {
                $injector.get('$controller')("TipoInvestimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:tipoInvestimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
