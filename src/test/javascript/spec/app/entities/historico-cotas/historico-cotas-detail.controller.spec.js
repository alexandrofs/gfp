'use strict';

describe('Controller Tests', function() {

    describe('HistoricoCotas Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHistoricoCotas, MockInvestimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHistoricoCotas = jasmine.createSpy('MockHistoricoCotas');
            MockInvestimento = jasmine.createSpy('MockInvestimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'HistoricoCotas': MockHistoricoCotas,
                'Investimento': MockInvestimento
            };
            createController = function() {
                $injector.get('$controller')("HistoricoCotasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:historicoCotasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
