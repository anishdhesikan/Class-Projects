#include <stdio.h>
#include <stdbool.h>
#include <assert.h>
//#include <sys/types.h>
//#include <unistd.h>
//#include <stdlib.h>
//#include <sys/wait.h>
#include <string.h>


/*
 * "As we will see in class, a  direct-mapped cache corresponds to
 * a 1-way set associative cache (n=1), and a fully associative corresponds
 * to a n-way set associative cache with n=s/l, the number of rows (lines) in
 * the cache."
 */
#define n_way 8 // number of blocks per set
#define s_cache_size 64 // size of cache
#define l_cache_line_size 8 // size of block

#define set_size (n_way * l_cache_line_size) // size of set
#define num_sets (s_cache_size / set_size) // number of sets

int test_set_1[] = {0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68, 72,
    76, 80, 0, 4, 8, 12, 16, 71, 3, 41, 81, 39, 38, 71, 15, 39, 11, 51, 57, 41};

// 0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68, 72, 76, 80, 0, 4, 8, 12, 16, 71, 3, 41, 81, 39, 38, 71, 15, 39, 11, 51, 57, 41

int *current_test = test_set_1;
int current_test_size = 39;

int timeStamp = 0;


struct cacheLine {
    int valid;
    int tag;
    int time;
//    struct cacheLine *next;
};;;;
struct cacheLine cache[s_cache_size];


// k is set size, tag is tag, index is set number
int isHitOrMissForSetAssoc( int k, int tag, int index );


int main()
{
    
    printf(" %d set_size ", set_size);
    
    printf(" %d num_sets \n", num_sets);
    
    for (int i = 0; i < current_test_size; i++) {
        int preindex = (current_test[i] / l_cache_line_size);
        
        int tag = preindex / num_sets;
        printf(" %d tag /", tag);
        
        int index = preindex % num_sets;
        printf(" %d index# /", index);
        
        // We declared isHitOrMissForSetAssoc above.  But we define it below.
        int answer = isHitOrMissForSetAssoc(n_way, tag, index);
        if (answer)
            printf("address(%d) Hit\n", current_test[i]);
        else
            printf("address(%d) Miss\n", current_test[i]);
        
    }
}

// evict the first line (FIFO style)
void evictFirstLine(int k, int index) {
    int minRowIndex = k*index;
    
    int rowIdx = 0;
    for ( rowIdx = k*index; rowIdx < k*index + k; rowIdx++ ) {
        if (cache[rowIdx].time < cache[minRowIndex].time ) {
            minRowIndex = rowIdx;
        }
    }
    
    cache[minRowIndex].valid = 0;
}

int isHitOrMissForSetAssoc( int k, int tag, int index ) {
    int isHit = 0;  // Initialize isHit to default value:  false
    int rowIdx = 0;
    // For set associative, index is the set number.
    for ( rowIdx = k*index; rowIdx < k*index + k; rowIdx++ ) {
        if ( cache[rowIdx].valid && cache[rowIdx].tag == tag ) {
            isHit = true;
            break;
        }
    }

    // Now, isHit has value true if and only if we found a hit.
    if (isHit) return 1; // return true
    
    // Else search for cache line with valid field == 0 (false)
    for ( rowIdx = k*index; rowIdx < k*index + k; rowIdx++ ) {
        if ( cache[rowIdx].valid == 0 ) {
//            struct cacheLine * myCacheLine = getFreeCacheLine(index);
            struct cacheLine myCacheLine = { 1, tag, timeStamp++ };
//            myCacheLine.tag = tag;
//            myCacheLine.valid = 1;
            cache[rowIdx] = myCacheLine;
            break;
        }
    }
    
    
    // If we didn't find a cache line with valid field false, then evict cache line
    if (rowIdx >= k*index + k) { // if failed to find invalid cache line
        
        // evict first cache line in set
        evictFirstLine(k, index);
        
        for ( rowIdx = k*index; rowIdx < k*index + k; rowIdx++ ) {
            if ( cache[rowIdx].valid == 0 ) {
                //            struct cacheLine * myCacheLine = getFreeCacheLine(index);
                struct cacheLine myCacheLine = { 1, tag, timeStamp++ };
                cache[rowIdx] = myCacheLine;
                break;
            }
        }
        
    }
    return isHit;
}