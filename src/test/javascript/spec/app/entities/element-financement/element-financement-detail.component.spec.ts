/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { ElementFinancementDetailComponent } from '../../../../../../main/webapp/app/entities/element-financement/element-financement-detail.component';
import { ElementFinancementService } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.service';
import { ElementFinancement } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.model';

describe('Component Tests', () => {

    describe('ElementFinancement Management Detail Component', () => {
        let comp: ElementFinancementDetailComponent;
        let fixture: ComponentFixture<ElementFinancementDetailComponent>;
        let service: ElementFinancementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ElementFinancementDetailComponent],
                providers: [
                    ElementFinancementService
                ]
            })
            .overrideTemplate(ElementFinancementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ElementFinancementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElementFinancementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ElementFinancement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.elementFinancement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
