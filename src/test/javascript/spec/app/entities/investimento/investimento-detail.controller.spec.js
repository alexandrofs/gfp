'use strict';

describe('Controller Tests', function() {

    describe('Investimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInvestimento, MockCarteira, MockTipoInvestimento, MockHistoricoCotas, MockInstituicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInvestimento = jasmine.createSpy('MockInvestimento');
            MockCarteira = jasmine.createSpy('MockCarteira');
            MockTipoInvestimento = jasmine.createSpy('MockTipoInvestimento');
            MockHistoricoCotas = jasmine.createSpy('MockHistoricoCotas');
            MockInstituicao = jasmine.createSpy('MockInstituicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Investimento': MockInvestimento,
                'Carteira': MockCarteira,
                'TipoInvestimento': MockTipoInvestimento,
                'HistoricoCotas': MockHistoricoCotas,
                'Instituicao': MockInstituicao
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
