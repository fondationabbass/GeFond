/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { ChapitreDetailComponent } from '../../../../../../main/webapp/app/entities/chapitre/chapitre-detail.component';
import { ChapitreService } from '../../../../../../main/webapp/app/entities/chapitre/chapitre.service';
import { Chapitre } from '../../../../../../main/webapp/app/entities/chapitre/chapitre.model';

describe('Component Tests', () => {

    describe('Chapitre Management Detail Component', () => {
        let comp: ChapitreDetailComponent;
        let fixture: ComponentFixture<ChapitreDetailComponent>;
        let service: ChapitreService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ChapitreDetailComponent],
                providers: [
                    ChapitreService
                ]
            })
            .overrideTemplate(ChapitreDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChapitreDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChapitreService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Chapitre(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.chapitre).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
