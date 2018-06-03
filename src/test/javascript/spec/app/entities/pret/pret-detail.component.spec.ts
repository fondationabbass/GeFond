/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { PretDetailComponent } from '../../../../../../main/webapp/app/entities/pret/pret-detail.component';
import { PretService } from '../../../../../../main/webapp/app/entities/pret/pret.service';
import { Pret } from '../../../../../../main/webapp/app/entities/pret/pret.model';

describe('Component Tests', () => {

    describe('Pret Management Detail Component', () => {
        let comp: PretDetailComponent;
        let fixture: ComponentFixture<PretDetailComponent>;
        let service: PretService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [PretDetailComponent],
                providers: [
                    PretService
                ]
            })
            .overrideTemplate(PretDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PretDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PretService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Pret(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pret).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
