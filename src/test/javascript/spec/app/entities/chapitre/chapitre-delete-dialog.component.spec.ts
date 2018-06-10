/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GeFondTestModule } from '../../../test.module';
import { ChapitreDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/chapitre/chapitre-delete-dialog.component';
import { ChapitreService } from '../../../../../../main/webapp/app/entities/chapitre/chapitre.service';

describe('Component Tests', () => {

    describe('Chapitre Management Delete Component', () => {
        let comp: ChapitreDeleteDialogComponent;
        let fixture: ComponentFixture<ChapitreDeleteDialogComponent>;
        let service: ChapitreService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ChapitreDeleteDialogComponent],
                providers: [
                    ChapitreService
                ]
            })
            .overrideTemplate(ChapitreDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChapitreDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChapitreService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
