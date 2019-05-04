/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { CarteiraComponent } from 'app/entities/carteira/carteira.component';
import { CarteiraService } from 'app/entities/carteira/carteira.service';
import { Carteira } from 'app/shared/model/carteira.model';

describe('Component Tests', () => {
    describe('Carteira Management Component', () => {
        let comp: CarteiraComponent;
        let fixture: ComponentFixture<CarteiraComponent>;
        let service: CarteiraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CarteiraComponent],
                providers: []
            })
                .overrideTemplate(CarteiraComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarteiraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteiraService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Carteira(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.carteiras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
