/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GeFondTestModule } from '../../../test.module';
import { CompteDialogComponent } from '../../../../../../main/webapp/app/entities/compte/compte-dialog.component';
import { CompteService } from '../../../../../../main/webapp/app/entities/compte/compte.service';
import { Compte } from '../../../../../../main/webapp/app/entities/compte/compte.model';
import { ClientService } from '../../../../../../main/webapp/app/entities/client';
import { PretService } from '../../../../../../main/webapp/app/entities/pret';
import { CaisseService } from '../../../../../../main/webapp/app/entities/caisse';
import { ChapitreService } from '../../../../../../main/webapp/app/entities/chapitre';

describe('Component Tests', () => {

    describe('Compte Management Dialog Component', () => {
        let comp: CompteDialogComponent;
        let fixture: ComponentFixture<CompteDialogComponent>;
        let service: CompteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CompteDialogComponent],
                providers: [
                    ClientService,
                    PretService,
                    CaisseService,
                    ChapitreService,
                    CompteService
                ]
            })
            .overrideTemplate(CompteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Compte(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.compte = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'compteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Compte();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.compte = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'compteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
