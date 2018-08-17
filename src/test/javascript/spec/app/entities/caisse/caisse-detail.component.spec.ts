/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { CaisseDetailComponent } from '../../../../../../main/webapp/app/entities/caisse/caisse-detail.component';
import { CaisseService } from '../../../../../../main/webapp/app/entities/caisse/caisse.service';
import { Caisse } from '../../../../../../main/webapp/app/entities/caisse/caisse.model';

describe('Component Tests', () => {

    describe('Caisse Management Detail Component', () => {
        let comp: CaisseDetailComponent;
        let fixture: ComponentFixture<CaisseDetailComponent>;
        let service: CaisseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CaisseDetailComponent],
                providers: [
                    CaisseService
                ]
            })
            .overrideTemplate(CaisseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CaisseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaisseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Caisse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.caisse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
