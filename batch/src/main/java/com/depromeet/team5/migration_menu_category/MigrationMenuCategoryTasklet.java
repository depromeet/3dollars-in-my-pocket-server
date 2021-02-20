package com.depromeet.team5.migration_menu_category;

import com.depromeet.team5.domain.store.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationMenuCategoryTasklet implements Tasklet {
    private final MigrationMenuCategoryService migrationMenuCategoryService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("migration menu category started");
        migrateMenuCategory();
        log.info("migration menu category ended");
        return RepeatStatus.FINISHED;
    }

    private void migrateMenuCategory() {
        Long storeId = 0L;
        int count = 0;
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "id"));
        while (true) {
            Slice<Store> stores = migrationMenuCategoryService.migrate(storeId, pageable);
            if (!stores.hasNext()) {
                break;
            }
            storeId = stores.getContent().get(stores.getNumberOfElements() - 1).getId();
            count += stores.getContent().size();
            log.info("storeId: {}, count: {}", storeId, count);
        }
    }
}
