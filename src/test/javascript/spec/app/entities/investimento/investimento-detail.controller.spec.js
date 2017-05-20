'use strict';

describe('Controller Tests', function() {

    describe('Investimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockInvestimento, MockCarteira, MockTipoInvestimento, MockHistoricoCotas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockInvestimento = jasmine.createSpy('MockInvestimento');
            MockCarteira = jasmine.createSpy('MockCarteira');
            MockTipoInvestimento = jasmine.createSpy('MockTipoInvestimento');
            MockHistoricoCotas = jasmine.createSpy('MockHistoricoCotas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Investimento': MockInvestimento,
                'Carteira': MockCarteira,
                'TipoInvestimento': MockTipoInvestimento,
                'HistoricoCotas': MockHistoricoCotas
            };
            createController = function() {
                $injector.get('$controller')("InvestimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:investimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});