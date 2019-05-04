/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { InstituicaoUpdateComponent } from 'app/entities/instituicao/instituicao-update.component';
import { InstituicaoService } from 'app/entities/instituicao/instituicao.service';
import { Instituicao } from 'app/shared/model/instituicao.model';

describe('Component Tests', () => {
    describe('Instituicao Management Update Component', () => {
        let comp: InstituicaoUpdateComponent;
        let fixture: ComponentFixture<InstituicaoUpdateComponent>;
        let service: InstituicaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InstituicaoUpdateComponent]
            })
                .overrideTemplate(InstituicaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InstituicaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InstituicaoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Instituicao(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.instituicao = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Instituicao();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.instituicao = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
